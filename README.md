### qrng

Random number generation via the ANU Quantum Random Numbers Server.  
These are purportedly 'truly random' numbers, based on measuring  
the quantum fluctuations of the vacuum.

### Usage

(qrand & opts)

You have 3 choices for option **:type**:  
* :int8  (an integer between 0-255, the default)  
* :int16  (an integer between 0-65535)  
* :hex16  (a hexadecimal string between "00"-"ff")  
  
The **:blocks** option is relevant only for type :hex16.  
It sets the hexadecimal block length and must be a number  
between 1-1024. The default is 1.

The **:length** option is the number of random numbers  
to be returned. The default is 1.

If option **:https** is true, the connection will be  
established using the https protocol. Note, however,  
that presently the ANU server has an untrusted SSL cert.  
The default for this option is false (i.e., http).

### Examples

    (require '[naipmoro.qrng.core :as qrng])

one random integer between 0-255 :
  
    (qrng/qrand)  
    => [110]

; 6 random integers between 0-255  
(qrng/qrand :length 6)  
=> [67 225 141 118 46 102]

; 4 random integers between 0-65535  
(qrng/qrand :length 4 :type :int16)  
=> [24861 25422 60585 58192]

; 6 random hexadecimals (00-ff)  
(qrng/qrand :length 6 :type :hex16)  
=> ["f8" "45" "3b" "78" "06" "20"]

; 5 hexadecimal blocks of size 2 (0000-ffff)  
(qrng/qrand :length 5 :type :hex16 :blocks 2)  
=> ["07cb" "fcc6" "411b" "2780" "3a99"]

; connect to server using the https protocol  
(qrng/qrand :length 5 :type :hex16 :blocks 2 :https true)  
=> ["d34d" "7b40" "8c35" "3693" "fb2c"]

; a string of 2024 random hex characters  
(qrng/qrand :length 1 :type :hex16 :blocks 1012)  
=> ["554681a2c8a6...[snipped 2000 characters]...16faddb80522"]

The actual ANU API restricts the maximum length of the returned  
vector to 1024. We bypass that restriction by using a connection  
pool to repeatedly execute the get function while maintaining  
performance.

### License

Distributed under the Eclipse Public License, the same as Clojure.