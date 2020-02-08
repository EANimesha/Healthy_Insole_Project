#include <SoftwareSerial.h>

SoftwareSerial BTserial(2, 3);//tx rx

void setup() {
  Serial.begin(9600);
  Serial.println("Arduino Bluetooth demo is running");
  BTserial.begin(9600);
}

void loop() {
  int sensorValue = analogRead(A0);
  float voltage = sensorValue * (5.0 / 1023.0);

  BTserial.print(voltage);
  BTserial.println(':');
  
  delay(1000);
  Serial.println(voltage);
}
