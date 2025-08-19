(ns naming-values-def.core
  (:gen-class))

(defn -main
  [& args]
  ;; binding a name to some value
  (def some-bind
    [ "some-value-1" "some-value-2" "some-value-3" ])
  (println some-bind)

  ;; Changing again with def is a bad practice. It's harder to understand!
  (println "Hello, World!"))
