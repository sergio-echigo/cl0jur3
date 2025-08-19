(ns data-structures.core
  (:gen-class))

(defn -main
  [& args]
  ;; clojure numbers:
  (def some-valid-numbers
    [ 1 2 3 1/2 3.5 ])

  ;; clojure strings are denoted only by double quotes:
  (def some-valid-strings
    [ "hello" "my" "little" "world" ])

  ;; concatenating strings
  (println (str "Hello, " "world!"))
  (println "Hello, World!"))
