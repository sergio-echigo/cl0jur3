(ns destructuring.core
  (:gen-class))

;; the idea behind is to bind a name to elements within
;; some collection.
(defn print-first-and-ignore-the-rest
  [[first-to-print]]
  (println first-to-print))

(defn print-the-rest-and-ignore-the-first
  [[first & remaining]]
  (map println remaining))

;; destructuring can be applied to maps too:
(defn print-from-map
  [{some-key :some-key another-key :another-key}]
  (println some-key)
  (println another-key))

(defn print-from-map-again-again
  [{:keys [some-key another-key] :as whole-map}]
  (println some-key)
  (println another-key)
  (println (str "The whole map is: " whole-map)))

(defn -main
  [& args]
