(ns map-as-reduce.core
  (:gen-class))

(defn map-as-reduce
  [fn-apply coll]
  (reduce #(conj % (fn-apply %2)) [] coll))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
