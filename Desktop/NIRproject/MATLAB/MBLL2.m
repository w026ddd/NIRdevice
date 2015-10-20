% Diogo Dias
% Master Thesis
% Wright State University

%MBLL modified beer lambert law in Matlab
% The output are the [hb] and [hbo] and the inputs are the OD of each wavelength
function [hb,hbo] = MBLL2(pa,pb)
%t1 is the initial time of the design
t1 = 0.1;
%the hb and hbo arrays are initialized
hb = pa;
hbo = pa;
%den is the denominator of the functions and the other values are constants
den = (0.335*0.2764 - 0.159*0.191)*2.1;

% the MBLL calculation start for every single value
for i = 1 : length(pb)-1
hb(i,1) = t1;
hbo(i,1)= t1;

%The MBLL calculations are dne
   hb(i,2) = ((0.191*(-log(pa(i,2))/6.25)) - (0.159*(-log(pb(i,2))/6.45)))/den ;

    hbo(i,2) = ((0.335*(-log(pb(i,2))/6.45)) - (0.2764*(-log(pa(i,2))/6.25)))/den;
    
t1 = t1 + 0.4;

%DPF constants to each wavelength
%6.25
%6.45
end
