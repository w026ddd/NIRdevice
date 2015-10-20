# Diogo Dias 
# Master Thesis 
# Wright State University 
# Oct 2015


# These commands are responsible to create the logfile to write data on it ( file names datalog), the flag "a+" means
# that every new text is going to be added at the end of the file
f = open('/root/datalog','a+')
# t is the time variable indicating how long has it passed since the system starts 
t = 0


f.write('\n timer,pd1  ,pd2  ,NIRLED \n')

f.close()

#infinite loop
while True:
    # the identation is essential to python script language
    f = open('/root/datalog','a+')
    # This command activate one of the LEDs
    GPIO.output("P8_12", GPIO.HIGH)
    # Data is read once
    ADC.read("P9_35")
    # Data is read again to clean the buffer and the variable is stored by on variable a
    a = ADC.read("P9_35")
    # the same happen to variable b
    ADC.read("P9_40")
    b = ADC.read("P9_40")
    # This both variables are written with the command below
    f.write('%1.3f,%1.3f,%1.3f,850nm \n' % (t,a,b))
    # As said before the file must be closed on the loop to make the program not crash
    f.close()
    # The LED is deactivated
    GPIO.output("P8_12", GPIO.LOW)
    # 0.2s is passed to the next point to be read
    time.sleep(0.2)
    # The time variable is updated 
    t = t + 0.2
    # The procedure is then repeated with the other LED
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
    # Then everything start over

# I needed to import the GPIO and ADC controller packages besides the time package to control
# the Sources and detectors on this design
# For the Online mode it also needed the subprocess package to call the packages from an outsource than python
# Math is used to use some math operators used in this design
import Adafruit_BBIO.GPIO as GPIO
import Adafruit_BBIO.ADC as ADC
import time
import subprocess
import math

#This us the startup of the GPIO pins used to activate the LEDs and the ADC instrument
GPIO.setup("P8_12", GPIO.OUT)
GPIO.setup("P8_10", GPIO.OUT)
#These commands asure that both digital pins to Low level and not start the script activating a LED
ADC.setup()
GPIO.output("P8_10",GPIO.LOW)

# These commands are responsible to create the logfile to write data on it ( file names datalog), the flag "a+" means
# that every new text is going to be added at the end of the file
f = open('/root/datalog','a+')
# t is the time variable indicating how long has it passed since the system starts 
t = 0

# The First Line is written with this command below 
f.write('\n timer,pd1  ,pd2  ,NIRLED \n')
# Everytime the logfile is open it must be closed in the loop to avoid the program crash or corrupt the text file
f.close()

# the two values below represents the denominator of the MBLL calculation formulas, one at a distance of 7cm, and the other at the                          
den2 = 0.124 
den23 = 0.143
#these empty vectors are only used fror the calibration part of the project
data1 = [0] * 100 
data2 = [0] * 100 
data3 = [0] * 100 
data4 = [0] * 100 
timet = [0] * 100 
#control variable to control the  Full-vectors
runs = 0;

#Def commands that can be called in its own sistem are reponsible for calibration, and trust of the system 
# The first Def is responsible to calibrate the data during 30s,
def calib(data):
	media = sum(data)/len(data) 
        sd =  math.sqrt(sum((i-media)**2 for i in data)/len(data)) 
	return [media,sd]

# The second Def is responsible to apply the averafing filter to data
def refine(pt1,me,sde):
	if pt1 > me:
		pt1 = pt1 - sde
        else :
                pt1 = pt1 + sde
	return pt1

# The third and last Def is responsible to apply the MBLL to filtered data
def MBLL2(l1,l2):
	od1 = -math.log(l1)
	od2 = -math.log(l2)
        hb = ((0.191*(od1/6.25)) - (0.159*(od2/6.45)))/den2
	hbo = ((0.335*(od2/6.45)) - (0.2764*(od1/6.25)))/den2
	return [hb,hbo] 

#infinite loop
while True:
    # the identation is essential to python script language
    f = open('/root/datalog','a+')
    # This command activate one of the LEDs
    GPIO.output("P8_12", GPIO.HIGH)
    # Data is read once
    ADC.read("P9_35")
    # Data is read again to clean the buffer and the variable is stored by on variable a
    a = ADC.read("P9_35")
    # the same happen to variable b
    ADC.read("P9_40")
    b = ADC.read("P9_40")
    # This both variables are written with the command below
    f.write('%1.3f,%1.3f,%1.3f,850nm \n' % (t,a,b))
    # As said before the file must be closed on the loop to make the program not crash
    f.close()
    # The LED is deactivated
    GPIO.output("P8_12", GPIO.LOW)
    # 0.2s is passed to the next point to be read
    time.sleep(0.2)
    # The time variable is updated 
    t = t + 0.2
    # The procedure is then repeated with the other LED
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
    
    # The system will run 100 times to get it calibrated  
    if runs < 100 :
	data1[runs] = a
	data2[runs] = b
	data3[runs] = c
	data4[runs] = d
    # When the calibrated new data is captured, it is divided to the other 4 arrays that work are going to use it
    elif runs == 100 :
        msd1 = calib(data1)
	mds2 = calib(data2)
        mds3 = calib(data3)
	mds4 = calib(data4)
        t =  0
        # Lastly When the calibrated new data is captured, and then it`s possible to make a sone about the sand
    else :
    	# The averaging filter is used on data
        tr1 = refine(c,mds3[0],mds3[1])
        tr2 = refine(a,mds2[0],mds2[1])
        # The MBLL is used 
        pr = MBLL2(tr1,tr2) 
        
        # It writes the data on the smartphone logfile  
        string1 = "%.2f,%.2f,%.2f" % (t,pr[0],pr[1])
        
        #The subprocess.Popen is used to call the function to send the data to a bluetooth device
        subprocess.Popen(["/root/./writer","-a",string1])

	# A run is added to the calibration variable, when it happens 100 times the calibration process is suspended and only 
	# the MBLL data is read or showed. 
	
    runs = runs + 1

