(ns mutability-with-atoms.lesson6
  (:use [clojure pprint])
  (:require
    [mutability-with-atoms.model :as h.model]
    [mutability-with-atoms.logic :as h.logic]))

(defn fits-in-queue?
  [queue]
  (-> queue
      count
      (< 5)))

(defn arrived-at [queue patient]
  (if (fits-in-queue? queue)
    (conj queue patient)
    (throw (ex-info "The queue is full" {:trying-to-add patient}))))


(defn arrived-at!
  [hospital patient]
  (let [queue (get hospital :g-queue)]
    (ref-set queue (arrived-at @queue patient))
    ))

(defn arrived-at!
  [hospital patient]
  (let [queue (get hospital :g-queue)]
    (alter queue arrived-at patient)
    ))

(defn async-arrived-at!
  [hospital patient]
  (future
    (Thread/sleep (rand 5000))
    (dosync
      (println "Trying synchronized code for " patient)
      (arrived-at! hospital patient)
      )))

(defn simulate-a-day
  []
  (let [hospital {:g-queue (ref h.model/empty-queue)
                  :lab-1   (ref h.model/empty-queue)
                  :lab-2   (ref h.model/empty-queue)
                  :lab-3   (ref h.model/empty-queue)
                  }]
    (dosync
      (arrived-at! hospital "William")
      (arrived-at! hospital "Mary")
      (arrived-at! hospital "Lucy")
      (arrived-at! hospital "Daniela")
      (arrived-at! hospital "Anna")
      ;(arrived-at! hospital "Paul")
      )
    (pprint hospital)
    ))

;(simulate-a-day)

(defn simulate-a-day-async-version
  []
  (let [hospital {:g-queue (ref h.model/empty-queue)
                  :lab-1   (ref h.model/empty-queue)
                  :lab-2   (ref h.model/empty-queue)
                  :lab-3   (ref h.model/empty-queue)
                  }]

    (def futures (mapv #(async-arrived-at! hospital %) (range 10)))
    ;(dotimes [patient 10]
    ;  (async-arrived-at! hospital patient))

    (future
      (dotimes [n 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))
    ))

(simulate-a-day-async-version)