# Diogo Dias 
# Master Thesis 
# Wright State University 
# Oct 2015

# I needed to import the GPIO and ADC controller packages besides the time package to control
# the Sources and detectors on this design
import Adafruit_BBIO.GPIO as GPIO
import Adafruit_BBIO.ADC as ADC
import time

#This us the startup of the GPIO pins used to activate the LEDs and the ADC instrument
GPIO.setup("P8_12", GPIO.OUT)
GPIO.setup("P8_10", GPIO.OUT)
ADC.setup()
#These commands asure that both digital pins to Low level and not start the script activating a LED
GPIO.output("P8_10",GPIO.LOW)
GPIO.output("P8_12",GPIO.LOW)

# These commands are responsible to create the logfile to write data on it ( file names datalog), the flag "a+" means
# that the
f = open('/root/datalog','a+')
# t is the time variable indicating how long has it passed since the system starts 
t = 0

# The First Line is written with this command below 
f.write('\n timer,pd1  ,pd2  ,NIRLED \n')
# Everytime the logfile is open it must be closed in the loop to avoid the program crash or corrupt the text file
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
