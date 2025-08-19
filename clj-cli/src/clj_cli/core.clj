(ns clj-cli.core
  (:gen-class))

;; create tasks
;; update tasks
;; mark task as complete
;; list tasks by priority
;; view task detailed information
;; delete tasks

(defn- create-task
  ([task-list name description due-date]
   (conj task-list {:name name :description description :due-date due-date})))

(defn- update-task
  [task-list task-id name description due-date]
  

(defn -main
  "Program's entry point."
  [& args])
