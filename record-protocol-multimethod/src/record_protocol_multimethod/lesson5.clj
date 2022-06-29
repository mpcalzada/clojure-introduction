(ns record-protocol-multimethod.lesson5
  (:use [clojure.pprint])
  (:require [record-protocol-multimethod.logic :as r.logic]))

(defn authorizer-type
  [request]
  (let [patient (:patient request)
        situation (:situation patient)]
    (cond (= :urgent situation) :always-authorize

          (contains? patient :health-insurance) :check-insurance

          :else :check-exam-cost)))

(defmulti needs-authorization-for-request?
          authorizer-type)

(defmethod needs-authorization-for-request?
  :always-authorize
  [_]
  false)

(defmethod needs-authorization-for-request?
  :check-insurance
  [request]
  (not (some #(= %1 (:exam request)) (:health-insurance (:patient request)))))

(defmethod needs-authorization-for-request?
  :check-exam-cost
  [request]
  (>= (:cost request) 50))


(let [private-patient {:id 15, :name "William", :birthdate "09/18/1981", :situation :regular}
      insured-patient {:id 15, :name "William", :birthdate "09/18/1981", :health-insurance [:x-ray :ultrasound], :situation :regular}]
  (pprint (needs-authorization-for-request? {:patient private-patient, :cost 40, :exam :blood-test}))
  (pprint (needs-authorization-for-request? {:patient insured-patient, :cost 1000, :exam :ultrasound}))
  )