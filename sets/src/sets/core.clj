(ns sets.core
  (:gen-class))

(defn -main
  [& args]
  ;; sets are collections of unique values.
  ;; there's also the types hash sets and sorted sets.
  ;; see a hash set literal:
  #{"albert" 20 "human"}

  ;; the hash-set function can be used to create a hash set:
  (hash-set 1 1 2 2) ;; the returned value is going to be #{ 1 2 }

  ;; the conj function can be used to add more items to the set:
  (conj #{ 1 2 3 } 4)

  ;; creating a set by a vector or list:
  (set [ 3 3 4 4 ])

  ;; it is possible to verify some value with the contains function:
  (contains? #{:a :b :c} :b)

  ;; keywords can be used for the same purpose too:
  (:b #{:a :b})

  ;; get function:
  (get #{:a :b} :a)
  (println "Hello, World!"))
