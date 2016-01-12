(defproject naipmoro/qrng "0.5.0"
  :description "A clojure interface to the ANU Quantum Random Numbers Server"
  :url "https://github.com/naipmoro/qrng"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo} 
  :scm {:name "git"
        :url "https://github.com/naipmoro/qrng"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-http "2.0.0"]]
  :profiles {:dev {:dependencies [[criterium "0.4.3"]
                                  [midje "1.8.3"]]}}
  :min-lein-version "2.0.0")
