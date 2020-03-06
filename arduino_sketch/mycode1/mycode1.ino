#include "DHT.h"
#define DHTPIN 2     
#define DHTTYPE DHT11   

DHT dht(DHTPIN, DHTTYPE);

int fsrPin[]={0,1,2,3,4}; 

int fsrReading;     // the analog reading from the FSR resistor divider
int fsrVoltage;     // the analog reading converted to voltage
unsigned long fsrResistance;  // The voltage converted to resistance, can be very big so make "long"
unsigned long fsrConductance; 
long fsrForce;       // Finally, the resistance converted to force
String str="";


void setup(void) {
  Serial.begin(9600);   // We'll send debugging information via the Serial monitor
  dht.begin();
}
 
void loop(void) {
    str="";
  for(byte i=0;i<5;i=i+1){
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
    }
  }
//  float h = dht.readHumidity();
  int t = dht.readTemperature();
  int f = dht.readTemperature(true);

    str+="_";
    str+=t;
    Serial.println(str);
    delay(1000);
}
