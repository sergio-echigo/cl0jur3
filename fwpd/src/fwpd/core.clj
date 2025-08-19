(ns fwpd.core
  (:gen-class))

;; If the filename is a constant, we may bind
;; a nme for its value, like doing the following:
;; (def filename "suspects.csv")

;; Declaring a vector of keywords.
(def vampire-keys [:name :glitter-index])

;; Declaring a function that converts a string into an integer.
(defn str->int
  [str]
  (Integer. str))

;; Associating each field with a function.
(def conversions {:name identity :glitter-index str->int})

;; Declaring a function that applies a function based on the "vampire-key" value.
(defn convert
  [vampire-key value]
  ((get conversions vampire-key) value))

;; This function converts a CSV content into multiples vectors.
(defn parse
  "Convert a CSV into rows of columns."
  [string]
  (map #(clojure.string/split % #",") (clojure.string/split string #"\n")))

;; This is my version of mapify:
;; Unfortunately, OpenAI didn't like it :(.
;; (defn mapify 
;;   [collections]
;;   (reduce #(conj % (apply assoc {} (reduce concat (map vector vampire-keys %2)))) [] collections))

(defn mapify
  [collections]
  (map #(reduce (fn [vampire-map [k v]] 
                  (assoc vampire-map k (convert k v))) {} (map vector vampire-keys %)) 
       collections))

(defn glitter-filter-reduce
  [minimum-glitter records]
  (reduce #(if (>= (:glitter-index %2) minimum-glitter) (conj % %2) %) [] records))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;; First exercise: Turn the result of your glitter filter into a list of names.
(defn glitter-filter-ex1
  [minimum-glitter records]
  (into [] (map :name (filter #(>= (:glitter-index %) minimum-glitter) records))))

;; Second exercise: Write a function, `append`, which will append a new suspect to your list of suspects.
(defn append
  [suspects-list record]
  (conj suspects-list record))

;; Third exercise: Write a function, `validate`, which will check that `:name` and `:glitter-index` are present when you append. 
;; The `validate` function should accept two arguments: a map of keywords to validating functions, similar to conversions, and the record to be validated
(defn validate
  [schema record]
  (not (some #(= ((first %) record) nil) schema)))

;; Fourth exercise: Write a function that will take your list of maps and convert it back to a CSV string. You'll need to use the clojure.string/join function.
(defn maps-to-csv
  [records]
  (reduce #(str % (clojure.string/join "," (map second %2)) "\n") "" records))




(defn -main
  [& args]
  (let [file-path (first args)
        minimum-glitter-filter (str->int (second args))]
    (if (and file-path minimum-glitter-filter)
      (println (str "Filtered results: " (glitter-filter-ex1 minimum-glitter-filter (mapify (parse (slurp file-path))))))
      (println "Usage: lein run <FILE_PATH> <MINIMUM_GLITTER_FILTER>"))))
