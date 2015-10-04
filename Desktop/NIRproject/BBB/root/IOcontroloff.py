import Adafruit_BBIO.GPIO as GPIO
import Adafruit_BBIO.ADC as ADC
import time

GPIO.setup("P8_12", GPIO.OUT)
GPIO.setup("P8_10", GPIO.OUT)
ADC.setup()
GPIO.output("P8_10",GPIO.LOW)

f = open('/root/datalog','a+')
t = 0

f.write('\n timer,pd1  ,pd2  ,NIRLED \n')
f.close()

while True:
    f = open('/root/datalog','a+')
    GPIO.output("P8_12", GPIO.HIGH)
    ADC.read("P9_35")
    a = ADC.read("P9_35")
    ADC.read("P9_40")
    b = ADC.read("P9_40")
    f.write('%1.3f,%1.3f,%1.3f,850nm \n' % (t,a,b))
    f.close()
    GPIO.output("P8_12", GPIO.LOW)
    time.sleep(0.2)
    t = t + 0.2
    f = open('/root/datalog','a+')
    GPIO.output("P8_10",GPIO.HIGH)
    ADC.read("P9_35")
    ADC.read("P9_35")
    c = ADC.read("P9_35")
    ADC.read("P9_40")
    d = ADC.read("P9_40")
    f.write('%1.3f,%1.3f,%1.3f,765nm \n' % (t,c,d))
    f.close()
    GPIO.output("P8_10", GPIO.LOW)
    time.sleep(0.2)
    t = t + 0.2

