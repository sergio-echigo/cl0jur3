(ns guessing-game.core
  (:gen-class))

;; 1. get user input;
;; 2. check wheter input is valid -> if not, come back to 1 with a warning;
;; 3. compare the input against the answer;

(declare compare-guess prompt-user-guess get-user-input handle-invalid-guess handle-correct-guess)

(def guess-hint
  {> (partial handle-invalid-guess >)
   = handle-correct-guess 
   < (partial handle-invalid-guess <)})



(defn start-game
  []
  (let [answer (rand-int 101)]
    (prompt-user-guess answer)))

(defn prompt-user-guess
  "Prompts the user for its guess and returns it."
  [answer]
  (try
    (println "Which is your guess? :)")
    (let [user-guess (Integer/parseInt (get-user-input))]
      (compare-guess answer user-guess))
    (catch NumberFormatException e
      (println "Invalid guess provided. Please, try again.\n")
      (prompt-user-guess answer))))

(defn compare-guess
  [answer guess]
  ((get guess-hint (some (fn [[k v]] (when (k answer guess) k)) guess-hint)) answer))


(defn get-user-input
  "Returns the user input."
  []
  (if-let [user-input (read-line)]
    (clojure.string/trim user-input)
    ""))


(defn handle-invalid-guess
  [function answer]
  (if (= function >)
    (println "Too big!")
    (println "Too low!"))

  (prompt-user-guess answer))

(defn handle-correct-guess
  [answer]
  (println "Nice, you won the game! Do you want to play again?"))


(defn -main
  [& args])
