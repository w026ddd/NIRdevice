% This function split the raw data file acquired by the device which has the following form
% t   pd1 pd2 wavelength
% 0.2 0.3 0.4 765nm
% 0.4 0.5 0.6 850nm
% . . .
%where the filename is the logfile .csv value and interval are the cells which you want to pick for this signal

function [pd1x765,pd2x765,pd1x850,pd2x850] = rawdataread(filename,interval)

%make the strings the same size
sheetname=strrep(filename,'.csv','');
%readfile
e1 = xlsread(filename,sheetname,interval);

% split the wavelength lines into two arrays odd and even 
 even = e1(2:2:end,:); %765nm
 odd = e1(1:2:end,:); %850nm

 %An array for eachdetector and for each wavelength
 pd1x850 = [odd(1:end,1) odd(1:end,2)];
 pd2x850 = [odd(1:end,1) odd(1:end,3)];
 pd1x765 = [even(1:end,1) even(1:end,2)];
 pd2x765 = [even(1:end,1) even(1:end,3)];

 %how to plot it
 %plot(pd1x850(1:end,1),pd1x850(1:end,2))
 
end
