(ns functions.core
  (:gen-class))

;; function definition is composed by the keyword "defn", 
;; the function name, the docstring (optional), parameters and its body.
;; the following function is a multi-arity one. it may be useful for passing
;; default values.
(defn hello-for-who
  ([name]
    (println (str "Hello," name "!")))
  ([]
    (hello-for-who "unknown")))

;; presenting a function with a "rest parameter", in which the function
;; may receive "n" args:
(defn map-inc-to
  [& numbers]
  (map inc numbers))

;; clojure always returns the last evaluated form.

;; using a function to return another one:
(defn inc-maker
  [inc-by]
  #(+ % inc-by))

(defn -main
  [& args]
  ;; anonymous function 
  (fn [name last-name] (println (str "Hello, " name " " last-name)))
  #(* % 3)

  (#(* 3 %) 8) ;; returns 24
  (#(* 3 % %2) 1 2) ;; returns 6

  ;; it is possible to bind a name to a function too!
  (def an-anonymous-function
    (fn [] (println "This is an anonymous function.")))

  (an-anonymous-function)
  (#(* % 3) 9)
  
  (def inc2
    (inc-maker 2))
  
  (inc2 3))
