#include "DHT.h"
#define DHTPIN 2     
#define DHTTYPE DHT11   

DHT dht(DHTPIN, DHTTYPE);

int fsrPin[]={0,1,2}; 

int fsrReading;     // the analog reading from the FSR resistor divider
int fsrVoltage;     // the analog reading converted to voltage
unsigned long fsrResistance;  // The voltage converted to resistance, can be very big so make "long"
unsigned long fsrConductance; 
long fsrForce;       // Finally, the resistance converted to force
String str="";


void setup(void) {
  Serial.begin(9600);   // We'll send debugging information via the Serial monitor
//  dht.begin();
}
 
void loop(void) {
    str="";
  for(byte i=0;i<3;i=i+1){
    str+="_";
    fsrReading = analogRead(fsrPin[i]); 
    fsrVoltage = map(fsrReading, 0, 1023, 0, 5000);
    if (fsrVoltage == 0) {
//      Serial.println("No pressure");
        str+="999";
    } else {
      fsrResistance = 5000 - fsrVoltage;     // fsrVoltage is in millivolts so 5V = 5000mV
      fsrResistance *= 10000;                // 10K resistor
      fsrResistance /= fsrVoltage;
      fsrResistance /=1000;                   //to make it kiloohms
      if(fsrResistance<10){
        str+="00";
        str+=fsrResistance;
      }else if(fsrResistance<100){
        str+="0";
        str+=fsrResistance;
      }else if(fsrResistance<1000){
        str+=fsrResistance;
      }else{
        str+="999";
      }
      
//      Serial.println(fsrResistance);
    }
  }
//
////temperature sensor
//  delay(2000);
//  float h = dht.readHumidity();
//  float t = dht.readTemperature();
//  float f = dht.readTemperature(true);
//
//  if (isnan(h) || isnan(t) || isnan(f)) {
//    Serial.println("Failed to read from DHT sensor!");
//    return;
//  }
//
//  float hif = dht.computeHeatIndex(f, h);
//  float hic = dht.computeHeatIndex(t, h, false);
//
//  Serial.print("Humidity: ");
//  Serial.print(h);
//  Serial.print(" %\t");
//  Serial.print("Temperature: ");
//  Serial.print(t);
//  Serial.print(" *C ");
//  Serial.print(f);
//  Serial.print(" *F\t");
//  Serial.print("Heat index: ");
//  Serial.print(hic);
//  Serial.print(" *C ");
//  Serial.print(hif);
//  Serial.println(" *F");
//
//  Serial.println("--------------------");
//  delay(2000);


    Serial.println(str);
    delay(1000);
//    Serial.println("#123_234_123_123_123_d12~");
//    delay(10000);
}
