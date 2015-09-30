function [pd1x765,pd2x765,pd1x850,pd2x850] = rawdataread(filename,interval)

sheetname=strrep(filename,'.csv','');
%readfile
e1 = xlsread(filename,sheetname,interval);

even = e1(2:2:end,:); %765nm
 odd = e1(1:2:end,:); %850nm

 %eachdetector for each wavelength
 pd1x850 = [odd(1:end,1) odd(1:end,2)];
 pd2x850 = [odd(1:end,1) odd(1:end,3)];
 pd1x765 = [even(1:end,1) even(1:end,2)];
 pd2x765 = [even(1:end,1) even(1:end,3)];

 %how to plot it
 %plot(pd1x850(1:end,1),pd1x850(1:end,2))
 
end