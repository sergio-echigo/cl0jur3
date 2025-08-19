(ns data-structures-maps.core
  (:gen-class))

(defn -main
  [& args]
  ;; there is hash maps and sorted maps. they're a way to associate some value to another one.
  ;; first-name and last-name are keywords.
  {:first-name "Albert"
   :last-name "Einstein"}
  
  {"sum-function" +}
  {:nesting-map {:first "Albert" :last "Einstein"}}

  ;; creating a hash map with the hash-map function
  (hash-map :a 1 :b 2)

  ;; retrieving a hash map item with the "get" function. the last arg is the default to return.
  (get {:a 0 :b 1} :b nil)

  ;; get-in allows the retrieval of nested maps -- the second args is a vector with n maps to lookup; the last arg
  ;; is the value to be found.
  (get-in {:some-value "some-value"
           :nesting-map {:a 1 :b 2}} [:nesting-map :a])

  ;; it is possible to treat the map as a function and return some value from it:
  ({:a 1 :b 2 :c 3} :c))
