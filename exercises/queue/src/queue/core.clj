(ns queue.core
  (:gen-class))

;; creates a new, empty queue:
(defn -queue
  []
  '())

;; inserts an element to the beginning of the queue:
(defn -push
  [queue element]
  (conj queue element))

;; removes the last element of the queue and returns it:

(defn -main
  [& args])
