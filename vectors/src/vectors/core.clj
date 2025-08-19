(ns vectors.core
  (:gen-class))

(defn -main
  [& args]
  
  ;; vectors are 0-indexed collections. 
  [ 3 2 1 ]
  
  ;; the get function can be used here too -- by providing the element index.
  (get [ 1 2 3] 0)
  
  ;; vector elements can be of any type, and it is possible to mix them too.
  ;; the vector function can create vectors too:
  (vector 1 2 3 4))
