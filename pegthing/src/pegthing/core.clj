(ns pegthing.core
  (:gen-class))

(declare user-entered-valid-move user-entered-invalid-move prompt move game-over query-rows prompt-rows)

(defn tri*
  "Returns a lazy sequence of triangular numbers. Each number represents
  how many positions are in the index of the number."
  ([]
   (tri* 0 1))
  ([sum n]
   (let [new-sum (+ n sum)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))

(def tri (tri*))

(defn triangular?
  "Checks whether the provided number is triangular or not."
  [n]
  (= (last (take-while #(>= n %) tri)) n))

(def not-triangular?
  (complement triangular?))

(defn row-tri
  "Returns the last number of the specified row."
  [row]
  (last (take row tri)))

(defn row-num
  "Returns the row number for the provided number."
  [pos]
  (inc (count (take-while #(> pos %) tri))))

(defn connect
  "Form a mutual connections between two positions."
  [board max-pos pos neighbor destination]
  (if (<= destination max-pos)
    (reduce #(let [[p1 p2] %2] 
               (assoc-in % [p1 :connections p2] neighbor))
            board [[pos destination] [destination pos]]) board))

(defn connect-right
  [board max-pos pos]
  (let [neighbor (inc pos)
        destination (inc neighbor)]
    (if (and (not-triangular? pos) (not-triangular? neighbor))
      (connect board max-pos pos neighbor destination)
      board)))

(defn connect-left-down
  [board max-pos pos]
  (let [neighbor (+ pos (row-num pos))
        destination (+ 1 (row-num pos) neighbor)]
    (if (< destination max-pos)
      (connect board max-pos pos neighbor destination)
      board)))

(defn connect-right-down
  [board max-pos pos]
  (let [neighbor (+ 1 pos (row-num pos))
        destination (+ 2 (row-num pos) neighbor)]
    (if (<= destination max-pos)
      (connect board max-pos pos neighbor destination)
      board)))

(defn add-pos
  "Returns a new board with the pegged position and its connections."
  [board max-pos pos]
  (let [pegged-board (assoc-in board [pos :pegged] true)]
    (reduce #(%2 % max-pos pos) pegged-board [connect-right connect-left-down connect-right-down])))

(defn new-board
  "Creates a new board based on the number of rows"
  [rows]
  (let [board {:rows rows}
        max-pos (row-tri rows)]
    (reduce #(add-pos % max-pos %2) board (range 1 (inc max-pos)))))

(defn pegged?
  [board pos]
  (get-in board [pos :pegged]))

(defn remove-peg
  [board pos]
  (assoc-in board [pos :pegged] false))

(defn place-peg
  [board pos]
  (assoc-in board [pos :pegged] true))

(defn move-peg
  [board source destination]
  (place-peg (remove-peg board source) destination))

(defn valid-moves-pos
  [board pos]
  (into {} (filter #(let [[dst, src] %] 
                      (and (not (pegged? board dst)) (pegged? board src))) 
                   (get-in board [pos :connections]))))

(defn valid-move? 
  [board src dst]
  (get (valid-moves-pos board src) dst))

(defn make-move
  [board src dst]
  (if-let [neighbor (valid-move? board src dst)]
    (move-peg (remove-peg board neighbor) src dst)))

(defn can-move?
  [board]
  (some (comp not-empty (partial valid-moves-pos board)) (map first (filter #(:pegged (second %)) board))))
  
(def alpha-start 97)
(def alpha-end 123)
(def letters (map (comp str char) (range alpha-start alpha-end)))
(def pos-chars 3)

(defn valid-char?
  [character]
  (let [ascii-code (int character)]
    (and (>= ascii-code alpha-start) (< ascii-code alpha-end))))

(defn render-pos
  [board pos]
  (str (nth letters (dec pos))
       (if (get-in board [pos :pegged])
         "\u001B[33m0\u001B[0m"
         "\u001B[31m-\u001B[0m")))

(defn row-positions
  "Return all positions in the give row."
  [row-num]
  (range (inc (or (row-tri (dec row-num)) 0))
         (inc (row-tri row-num))))

(defn row-padding
  [row-num rows]
  (let [pad-length (/ (* (- rows row-num) pos-chars) 2)]
    (apply str (take pad-length (repeat " ")))))

(defn render-row
  [board row-num]
  (str (row-padding row-num (:rows board))
       (clojure.string/join " " (map (partial render-pos board)
                                     (row-positions row-num)))))

(defn print-board
  [board]
  (doseq [row-num (range 1 (inc (:rows board)))]
    (println (render-row board row-num))))

(defn letter->pos
  [letter]
  (inc (- (int (first letter)) alpha-start)))

(defn get-user-input 
  ([] (get-user-input ""))
  ([default]
   (let [input (clojure.string/trim (read-line))]
     (if (empty? input)
       default
       (clojure.string/lower-case input)))))

(defn characters-as-strings
  [string]
  (map str (filter valid-char? string)))

(defn prompt-move
  [board]
  (println "\nHere's your board:")
  (print-board board)

  (println "Move from where to where? Enter two letters: ")
  (let [input (map letter->pos (characters-as-strings (get-user-input)))]
    (println "input is: " input)
    (if-let [new-board (make-move board (first input) (second input))]
      (user-entered-valid-move new-board)
      (user-entered-invalid-move board))))


(defn user-entered-invalid-move
  [board]
  (println "\n!!! INVALID MOVE PROVIDED. !!!")
  (prompt-move board))

(defn user-entered-valid-move
  [board]
  (if (can-move? board)
    (prompt-move board)
    (game-over board)))

(defn game-over
  [board]
  (let [remaining-pegs (count (filter :pegged (vals board)))]
    (println "!!! HA, GAME OVER! REMAING PEGS: " remaining-pegs ".")
    (print-board board)

    (println "Do you want to play again? [Y/N]")
    (let [input (get-user-input "y")]
      (if (= "y" input)
        (prompt-rows)
        (do
          (println "BYE!")
          (System/exit 0))))))


(defn prompt-empty-peg
  [board]
  (println "Here's your board:")
  (print-board board)

  (println "Remove which peg? [e]")
  (prompt-move (remove-peg board (letter->pos (get-user-input "e")))))

(defn prompt-rows
  []
  (println "How many rows? [5]")
  (let [rows (Integer. (get-user-input 5))
        board (new-board rows)]
    (prompt-empty-peg board)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (prompt-rows))

