%Diogo Dias UID# U00777095
%BME 7110 Biomedical Signals
% Project 3 
%
%--- help for freqanalysis() ---
%
%function included in HRandV function it plots the beat tachogram of the
%peaks , resamples the tachogram withh the double of the samples and then
%plots a PSD of the signal and lastly calculates the coherence ratio and
%other measurements on the frequency domain
%in order to run it it`s necessary to have a array with intervals of peak
%data , that can be obtained by the statistical function.
%it`s also necessary to input the sampling frequency here

function freqanalysis(Ival,fs)

%the array of the peak number is created
Btnumb = 1:length(Ival);
%the tachogram is plotted and labeled
subplot(2,1,1);
plot(Btnumb,Ival);
title('Plot of Intervals(s) vs. Peak #');
xlabel('Interval(s)');
ylabel('Peak #');
%the interval data is resampled with the double of its samples with the
%resample comand
Ivalres = resample(Ival,2,1);

%The psd is obtained firstly by using a periodogram of the resampled data,
%and then by using the dspdata.psd that makes an object with psd
%information of a set of data, the sampling frequency chosen for the psd was
%was 4*fs because we doubled the sampling frequency on the resempled signal 
% and in order to
% be able to do the last measurements
Pxx = periodogram(Ivalres);
hpsd = dspdata.psd(Pxx,'Fs',fs*4);%2*Fs
%the psd is plotted on the same window of the tachogram, below it
subplot(2,1,2);
plot(hpsd);

%The psd object had it`s power spectrum and frequencies arrays splitted
PSDf = hpsd.Frequencies;
PSDd = hpsd.data;

%The LF and HF power components were obtained through the lines below
%The numbers inside the PSDd array corresponds to the spectral range of 
%each band
LF = mean(PSDd(10:24));
HF = mean(PSDd(24:78));
VLF = mean(PSDd(1:10));
%The normalized LF and HF were caculated
LFnorm = LF-VLF;
HFnorm = HF-VLF;
%The integrals for calculating the peak and total power used the trapz
%function of the MATLAB that realizes numeric integration
Ppower = trapz(PSDd(5:10));
Tpower = trapz(PSDd(1:78));
%The coherence ratio is calculated below
Cohratio = Ppower/(Tpower-Ppower);

%The calculated data is showed below  
sprintf(' LF[ms^2]\t\t\t\t %.2f \n LF normalized[ms^2]\t %.2f \n HF[ms^2]\t\t\t\t %.2f \n HF normalized[ms^2]\t %.2f\n Coherence ratio \t\t %.2f',LF,LFnorm,HF,HFnorm,Cohratio)
end