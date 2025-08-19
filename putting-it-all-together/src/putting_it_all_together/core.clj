(ns putting-it-all-together.core
  (:gen-class))

;; defining hobbit parts
(def hobbit-body-parts
  [{:name "head" :size 3}
   {:name "neck" :size 2}
   {:name "left-arm" :size 4}
   {:name "left-hand" :size 1}
   {:name "trunk" :size 5}
   {:name "left-leg" :size 7}
   {:name "left-foot" :size 1}])

(defn symmetrize-hobbit-body-part
  "Returns a hobbit body part but symmetric"
  [{name :name size :size}]
  {:name (clojure.string/replace name #"^left-" "right-") :size size})

(defn symmetrize-hobbit-body-parts
  "Symmetrizes all hobbit body and returns"
  [asymmetric-hobbit-body-parts]
 (loop [symmetric-hobbit-body-parts []
        [actual-part & remaining-parts] asymmetric-hobbit-body-parts]
   (if actual-part
     (recur (into symmetric-hobbit-body-parts (set [actual-part (symmetrize-hobbit-body-part actual-part)])) remaining-parts)
     symmetric-hobbit-body-parts)))

(defn better-symmetrize-hobbit-body-parts
  "Symmetrizes all hobbit body parts, but using reduce."
  [asymmetric-hobbit-body-parts]
  (reduce #(into % (set [%2 (symmetrize-hobbit-body-part %2) ])) [] asymmetric-hobbit-body-parts))

(defn hit-hobbit-part
  "Randomly chooses a hobbit body part and then hits it!"
  [asymmetric-hobbit-body-parts]
  (let [symmetric-hobbit-body-parts (better-symmetrize-hobbit-body-parts asymmetric-hobbit-body-parts)
         hobbit-body-parts-size (reduce + (map :size symmetric-hobbit-body-parts))
         target-hobbit-body-part (rand hobbit-body-parts-size)]
    (println symmetric-hobbit-body-parts)
    (println (str "Target is " target-hobbit-body-part))
    (loop [[part & remaining] symmetric-hobbit-body-parts
           checked-parts-sum (:size part)]
      (if (> checked-parts-sum target-hobbit-body-part)
        part
        (recur remaining (+ checked-parts-sum (:size (first remaining))))))))



(defn -main
  [& args]
  (println (better-symmetrize-hobbit-body-parts hobbit-body-parts)))
