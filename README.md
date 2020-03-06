# Embedded Systems - Group Project

# University of Kelaniya

## By

## E A N Dilini - SE/2016/

## J A D U Shalinda - SE/2016/


```
Abstract
```
Normally most of the people wearing their shoes. So we can introduce a smart insole
that can be inserted into their shoe to identify some medical issues.
We are introducing an insole that can measure pressure distribution on foot and
temperature inside the shoes. We can analyze those measured values and identify whether
user’s foot is healthy or not.
This insole can identify diseases like the diabetic neuropathy and chronic foot
diseases (growth of microorganisms). Mainly we are targeting the audience of diabetic
patients.
.

```
Introduction
```
Diabetes is one of the major causes of illness and premature death worldwide.
Diabetes causes neurovascular complications, which result in the development of high
pressure areas in feet and hands.
Diabetic neuropathy causes nerve damage which can lead to amputation or
ulceration. Locating abnormal pressure patterns under the foot enables early detection of
neuropathy, preventing its serious consequences. [reference 1]

```
Fig1: Formation of ulcers from peripheral diabetic neuropathy
```
Another problem is, High temperature and humidity inside footwear. It has found
that there is a correlation between planter foot temperature and diabetic neuropathy.
In addition, excessive humidity may promote the growth of microorganisms (fungi
and bacteria) and cause chronic foot diseases. High temperature and humidity inside footwear
also affect blood flow, putting a strain on the vascular system in the lower extremities.

Our objective in this project is to design and build a low-cost pressure & temperature
measurement and analysis system based on an Arduino microcontroller, which a patient can
use at home to measure his or her foot pressure distribution.

We have successfully designed and built a prototype system using a set of five Film
Pressure sensors distributed on an insole. An Arduino microcontroller is used to measure the
pressure sensor outputs and transmit the information through Bluetooth to a mobile
application. The app can compare the pressure distribution against a reference distribution
and show any anomalies.
Users can insert this insole to their shoes if they need to check anomalies and easily
they can remove it when it is not using. The results show that such a device can be built at a
low cost and can accurately measure the foot pressure distribution to detect anomalies.


```
Method
```
Materials Used

```
 Film Pressure Sensors[DF9-40]
 Dht11 temperature sensor
 Arduino Uno Microcontroller
 Hc5 Bluetooth Module
 Jumper wires
```
Methods Workflow:

## 1. Analyzed the researched data about pressure distribution, temperature changes on

```
human foot and identified the specific sensor placement points
```
For Diabetic Neuropathy:

Abu-Faraj [reference 2] determined that sensors must be placed on eight areas of the
foot to measure the pressure distribution accurately. These areas are categorized into high,
medium and low-pressure areas.
 High-pressure areas: Heel, Metatarsal Head and Metatarsal Head 1
 Medium-pressure areas: Metatarsal Head 5, Toe and Arch 1
 Low-pressure areas: Fing and Arch 2
These areas are marked in the figure.

We placed the pressure sensors on a sole according to this layout.For the prototype selected
5 higher pressure points.[Heel, Metatarsal Head, Metatarsal Head 1, Metatarsal Head 5 and
Toe]

8 standard pressure points

```
We have selected
5 pressure point
for our prototype
```

For high temperature detection:

According to [reference 6 ], Patients with diabetic neuropathy (defined as
vibration perception threshold (VPT) values on biothesiometry greater than 20 V) had a
higher foot temperature ( 32 - 35 °C) compared to patients without neuropathy ( 27 - 30 °C).

According to referred research paper [reference 3], Under normal conditions
of use, relative humidity inside footwear amounts to 60 **_–_** 65 %. According to some research,
humans experience a sensation of comfort at foot temperatures in the range of 20 °C **_–_** 33 °C
and a sensation of discomfort at temperatures in the range 35 °C– 3 8 °C.

According to above references we send warning to user when the temperature
is more than 33°C.

## 2. Understanding the working of the force sensors

Selecting the force sensors:

We had many options for force measuring sensors. Film Pressure sensors, load cells,
etc. We have selected the Film Pressure sensors because those sensors are low cost, small in
size and high sensitivity.

Understanding the working principles of Film Pressure sensor:

According to data Sheet
[reference: 4]. The Film Pressure sensor
is based on a force-sensing resistor;
whose resistance varies inversely with the
applied force. With no load, the
resistance is very high (more than 1 Mega
Ohm). As load is applied, the resistance
decreases as shown below.

fig.

By connecting it in an electrical circuit, the change in resistance can be converted to a change
in voltage, which can then be sensed by the Arduino microcontroller. The following circuit
can be used to convert the change in the resistance of the sensor to a change in voltage.


## 3. Connect sensors with Arduino Uno microcontroller

```
We have used 5 analog pins to read analog data values from pressure sensors and a
single digital pin to read temperature data values. For the circuit diagram refer diagram
[circuit diagram at page 9]
```
```
Through analog data pins microcontroller reads the voltage values through each sensor
and convert it to a resistance presented in kilo ohms. According to the resistance force
diagram[fig.3] we can calculate the force in newton. The resistance is infinite when
there is no pressure. So we assign 999 default value when there is no pressure exerting
on sensors. [999 means no pressure exerting]. When we exert pressure on sensors the
resistance decreased.
```
```
Temperature value is read from a digital pin. We used the dht sensor library for that.
```
```
Then we send the 6 data values [5 pressure sensor values+ temperature] as a formatted
string through the Bluetooth module.
```
Format: _FSensor1_Fsensor2_Fsensor3_Fsensor4_Fsensor5_tempertaureSensor

For the full source code refer the code [in page 10 & 11], For the algorithm refer the
flowchart [ in page 13]

## 4. Selecting a communication media[Bluetooth]

```
We have selected the Bluetooth rather than Wi-Fi or any other medium because
Bluetooth is,
```
```
Used for short range data transmission
Low power consumption
```

## 5. Designed the mobile application

```
We build the mobile app in android studio with java language. The application is able
to display the healthy level of the users after connecting the insole system to it.
This application receives data string from Bluetooth and decompose the string to
identify different sensor values. It gathers 5 data strings and get the average value. The
average value is compared with the normal pressure and temperature values to predict
the healthy status of user.
The flowchart diagram at [Flowchart at page 12] represents the algorithm that
we used to build our mobile app.
```
```
Results
```
Serial print output from Arduino ide

```
Serial print results:
```

Results from the mobile application

```
budget
```
```
 Film Pressure Sensors[DF9-40] - Rs.1800($10.49)
 Dht11 temperature sensor - Rs.
 Arduino Uno Microcontroller - Rs.
 Hc5 Bluetooth Module - Rs.
 Jumper wires - Rs.
 Rubber Insole - Rs.
Total - Rs.
```
The price of a Diabetic Foot Ulcers Test Machine Bio Thesiometer VPT for Neuropathy

Diagnosis from an online shop. The machine price is about $1350. So we can market out

product with low price by further developing it. We can market our product to Rs.5000 with

all production costs.

```
Conclusion & Discussions
```
We have successfully designed the smart insole porotype. It can identify the abnormal
change of pressure and temperature. For further developments we need to do some practical
trainings of the device with diabetic patients. After further analyzing of data with a machine
learning models we can develop the product to identify users walking patterns, wrong
postures of walking and to predict human body weight with pressure values.

```
Initial stage Checking the current
state by comparing with
normal averages
```

```
Resources
```
All the resources of project can be found at:
https://github.com/EANimesha/Healthy_Insole_Project

```
References
```
## 1. A Real Time Foot Pressure Measurement for Early Detection of Ulcer

```
Formation in Diabetics Patients Using Labview. A.N.Nithyaa, R.Premkumar^
,S.Dhivya^ ,M.Vennila[url:
https://www.sciencedirect.com/science/article/pii/S1877705813017256]
```
## 2. Z. O. Abu-Faraj, G. F. Harris, A-H. Chang, M. J. Shereff, “Planter Pressure

```
Alterations with Scphoid Pad,” IEEE Transactions on Rehabilitation
Engineering, Vol. 4, Dec. 1996,pp. 328–336.
```
## 3. AUTEX Research Journal, Vol. 16, No 2, June 2016, DOI: 10.1515/aut- 2015 -

### 0030 © AUTEX,” THE MICROCLIMATE IN PROTECTIVE FIRE FIGHTER

### FOOTWEAR: FOOT TEMPERATURE AND AIR TEMPERATURE AND

```
RELATIVE HUMIDITY”, Emilia Irzmańska[url:
https://www.degruyter.com/downloadpdf/j/aut.2015.16.issue-2/aut- 2015 -
0030/aut- 2015 - 0030.pdf]
```
## 4. Film Pressure Sensor DF9-40@10kg V2.0 datasheet [url: https://www.winsen-

```
sensor.com/d/files/df9-40%4010kg.pdf]
```
## 5. HC- 05 - Bluetooth to Serial Port Module. Datasheet [url:

```
http://www.electronicaestudio.com/docs/istd016A.pdf]
```
## 6. Correlation between plantar foot temperature and diabetic neuropathy: a case

```
study by using an infrared thermal imaging technique. J Diabetes Sci
Technol. [url: https://www.ncbi.nlm.nih.gov/pubmed/21129334]
```
## 7. Arduino-Based Foot Neuropathy Analyzer[url:

```
https://learn.parallax.com/sites/default/files/inspiration/818/dl/Foot-
Neuropathy-Analyzer-Report.pdf]
```
