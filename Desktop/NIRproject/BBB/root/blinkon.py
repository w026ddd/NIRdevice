import Adafruit_BBIO.GPIO as GPIO
import Adafruit_BBIO.ADC as ADC
import time
import subprocess
import math

GPIO.setup("P8_12", GPIO.OUT)
GPIO.setup("P8_10", GPIO.OUT)
ADC.setup()
GPIO.output("P8_10",GPIO.LOW)

f = open('/root/datalog','a+')
t = 0

f.write('\n timer,pd1  ,pd2  ,NIRLED \n')
f.close()

den2 = 0.124 
den23 = 0.143
data1 = [0] * 100 
data2 = [0] * 100 
data3 = [0] * 100 
data4 = [0] * 100 
timet = [0] * 100 
runs = 0;

def calib(data):
	media = sum(data)/len(data) 
        sd =  math.sqrt(sum((i-media)**2 for i in data)/len(data)) 
	return [media,sd]

def refine(pt1,me,sde):
	if pt1 > me:
		pt1 = pt1 - sde
        else :
                pt1 = pt1 + sde
	return pt1

def MBLL2(l1,l2):
	od1 = -math.log(l1)
	od2 = -math.log(l2)
        hb = ((0.191*(od1/6.25)) - (0.159*(od2/6.45)))/den2
	hbo = ((0.335*(od2/6.45)) - (0.2764*(od1/6.25)))/den2
	return [hb,hbo] 

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
    c = ADC.read("P9_35")
    ADC.read("P9_40")
    d = ADC.read("P9_40")
    f.write('%1.3f,%1.3f,%1.3f,765nm \n' % (t,c,d))
    f.close()
    GPIO.output("P8_10", GPIO.LOW)
    time.sleep(0.2)
    t = t + 0.2
    
    if runs < 100 :
	data1[runs] = a
	data2[runs] = b
	data3[runs] = c
	data4[runs] = d
    elif runs == 100 :
        msd1 = calib(data1)
	mds2 = calib(data2)
        mds3 = calib(data3)
	mds4 = calib(data4)
        t =  0
    else :
        tr1 = refine(c,mds3[0],mds3[1])
        tr2 = refine(a,mds2[0],mds2[1])
        pr = MBLL2(tr1,tr2) 
        string1 = "%.2f,%.2f,%.2f" % (t,pr[0],pr[1])
        subprocess.Popen(["/root/./writer","-a",string1])

    runs = runs + 1

