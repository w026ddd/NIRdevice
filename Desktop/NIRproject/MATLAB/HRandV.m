%Diogo Dias UID# U00777095
%BME 7110 Biomedical Signals
% Project 3
%
%--- help for HRandV ---
%
%HRandV function is the main function for analyzing the Heart Rate(HR) and
%the Heart Rate Variability(HRV), with time domain and frequency domain
%analysis of ecg data
%In order to run this program it needs ecgData in a .mat file, with 3
%subjects and with the name of the matrix of ecgRaw, as one of the inputs
%as a string 'File.mat', in the data slot, and in fs it`s necessary to 
%input the sampling frequency of the data in Hz
%
%There will be a lot of plots from the analysis wait the software to run the program
%for the finish its process to close or view them, 

function  HRandV(data,fs)
%comand for reading data
load(data);
%the use of parameters of the data matrix
%m is the lines(amount of data)
%n is the colums(amount of patients)
[m,n] = size(ecgRaw);

%spectra
DATA = fft(ecgRaw);
%plot(abs(DATA));

%cutoff = input('looking at the graph write the frequency you want to cutoff, in a lowpass filter?')
%order = input('what is the filter order you want?')

%automatic choice of the cutoff frequency , 10 because an human heart
%cannot beat more than 3Hz and at this frequency, and with this order for the filter
%the data has a useful response 
cutoff = 10;
order = 2;

%Part A- filterdata
%in this loop all the ecg data are filtered
%for (i=1:n)
%f(:,i) = lowpassfilter(ecgRaw(:,i),fs);
%end


%Part B- Time Domain analysis

%finding peaks(heartbeats)

%The command used for finding the R peaks in the filtered data was
%findpeaks comand, togheter with its option of MinPeakHeight(it only counts peaks
%that are bigger than a value),MinPeakDistance(it only counts peaks again within a 
%determined distance, and Threshold (only let it counts again if there is a
%value minor than the last peak. 
[p1,loc1] = findpeaks(f(:,1),'MinPeakHeight',12000,'MinPeakDistance',190,'Threshold',50);
%[p2,loc2] = findpeaks(f(:,2),'MinPeakHeight',12000,'MinPeakDistance',190,'Threshold',50);
%[p3,loc3] = findpeaks(f(:,3),'MinPeakHeight',12000,'MinPeakDistance',190,'Threshold',50);
%limitation
%[p(:,i),loc(:,i)] = findpeaks(f(:,i),....);
%couldnt be able to make this code above to work, becuase dimension mismatch
%so I`m doing it for this problem only for the 3 data

%statistical measurements in time domain
%conversion of the location vector into seconds 
loc1 = loc1*1/fs;
%writing the patient indication for the results
sprintf('patient %d',1)
%opening a new figure
%figure(2)
%setting the name of the plot
%set(2,'name','Heart Rate vs. Time for Patient #1');
%Running the statistical analysis in time domain for patient 1
I1 = statistical(loc1);
%analogue procedure as the patient 1
%loc2 = loc2*1/fs;
%sprintf('patient %d',2)
%figure(3)
%set(3,'name','Heart Rate vs. Time for Patient #2');
%I2 = statistical(loc2);
%analogue procedure as the patient 1
%loc3 = loc3*1/fs;
%sprintf('patient %d',3)
%figure(4)
%set(4,'name','Heart Rate vs. Time for Patient #3');
%I3 = statistical(loc3);

%frequency domain analysis

%opening a new window, naming the plot, and indicating the patient for the
%results.
figure(5)
set(5,'name','Beat tachogram and PSD for Patient #1');
sprintf('patient %d',1)
freqanalysis(I1,fs)
%figure(6)
%set(6,'name','Beat tachogram and PSD for Patient #2');
%sprintf('patient %d',2)
%freqanalysis(I2,fs)
%figure(7)
%set(7,'name','Beat tachogram and PSD for Patient #3');
%sprintf('patient %d',3)
%freqanalysis(I3,fs)

end%end of HRandV function

