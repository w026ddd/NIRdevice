% This code averanges the signal py 

function [px] = refine(py)

% px resulting array is initialized 
px = py;

% the mean and standard deviation of the signal py are calculated 
m = mean(py(1:end,2));
s = std(py(1:end,2));

% the py`s signal amplitude of signal is reduced by the technique resulting on px 
for i = 1:length(py)
   % if a point on th py is bigger than py`s mean the point is subtracted by the  py`s standard deviation
   if(py(i,2) > m)
   px(i,2) = py(i,2) - s;    
   % if a point on th py is shorter than py`s mean the point is added by the py`s standard deviation
   else
   px(i,2) = py(i,2) + s;    
   end
    
end
end
