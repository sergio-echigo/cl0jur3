(ns keywords.core
  (:gen-class))

(defn -main
  [& args]
  ;; keywords are primarily used as key in maps. some valid are:
  (def some-valid-keywords
    [:a :_? :34 :fewuifwef])
  
  ;; they can be used as functions to return values from data structures:
  (:a {:a 1 :b 2} :b)) ;; the last arg here is a default value.
