(ns lists.core
  (:gen-class))

(defn -main
  [& args]
  ;; "lists are like vectors, but with some differences"
  ;; the get function can't be used here.
  '(1 2 3)

  ;; retrieving with the nth function
  (nth '(1 2 3) 0)

  ;; creating a list with the list function
  (list 1 "two" :3)

  ;; elements are added to the beginning of the list:
  (conj '(3 2 1) 4))

  ;; if there's no necessity if adding elements to the begginning or to write a macro, vectors are
  ;; probably the best one to use.


