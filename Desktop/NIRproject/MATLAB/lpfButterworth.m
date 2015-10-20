% This function is used to low-pass butterworth filter x data at a sampling frequency of f, at a frequency f_cutoff
% and at a order order resulting in a filtered signal y
function [y] = lpfButterworth(x,f,f_cutoff,order)
% Implemention of a Low pass Butterworth filter using the filtfilt command
% User inputs to function
% x: input signal
% f: sampling frequency [Hz]
% f_cutoff: cutoff frequency [Hz]
% order: filter order
% Function outputs
% y: filtered signal
%% Design the filter---------------------------------------------------------------------
% Normalize the cut-off frequency
fnorm = f_cutoff/(f/2);
% For user-specified input order, create low-pass Butterworth filter
[b1,a1] = butter(order,fnorm,'low');
%% Implement the filter and view its response--------------------------------------------
% Generate filtered output y. Filtfilt function implements with zero phase
y = filtfilt(b1,a1,x);
% View the magnitude and frequency responses of the filter
freqz(b1,a1,128,f), title('LPF Characteristics');
end
