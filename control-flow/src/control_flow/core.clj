(ns control-flow.core
  (:gen-class))

(defn -main
  [& args]
  ;; if form -- only one form per if branch.
  (if (= 1 1 )
    (println "(= 1 1) is true!")
    (println "(= 1 1) is false!"))

  ;; do form -- executes multiple forms.
  (if (= 1 1) 
    (do (println "That's good, (= 1 1).")
        (println "Printing again...")
        "yes, (= 1 1).")
    (do (println "Sounds a little weird...")
        (println "Printing again...")
        "no, (= 1 1)."))

  ;; when form -- executes only the true branch, but allows multiple forms.
  (when (= 1 1)
    (println "That's good, (= 1 1).")
    (println "Printing again...")
    "yes, (= 1 1).")

  ;; checking if a value has a value or not:
  ;; Attention: nil and false are the only ones used to represent logical falsiness.
  (nil? nil) ; => true
  (nil? 1) ; => false

  ;; or => the first truthy value or the last value
  ;; and => the first falsey value or the last value
  (or false nil false 1) ; => 1
  (and true 1 2 3 4 5 6 false 7 8 9)) ; => false
