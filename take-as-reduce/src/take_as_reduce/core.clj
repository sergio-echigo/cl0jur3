(ns take-as-reduce.core
  (:gen-class))

(defn take-as-reduce
  [n coll]
  (reduce #(if (= n (count %)) (reduced %) (into % [%2])) [] coll))

(defn take-while-as-reduce
  [func coll]
  (reduce #(if (func %2) (into % [%2]) (reduced %)) [] coll))

(defn drop-as-reduce
  [n coll]
  (second (reduce #(let [[i r] %] (if (< i n) [(inc i) r] [(inc i) (into r [%2])])) [0 []] coll)))

(defn drop-while-as-reduce
  [func coll]
  (second (reduce #(let [[c r] %] (if c [c (into r [%2])] (if (func %2) [false r] [true (into r [%2])]))) [false []] coll)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
