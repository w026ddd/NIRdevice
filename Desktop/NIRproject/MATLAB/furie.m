function furie(data,fs)

X = fft(data);
N = length(data);
        %building a array of frequencies 
ws = 2*pi/N;
wnorm = -pi:ws:pi;
wnorm = wnorm(1:length(data));
w = wnorm*fs;
        %plotting the spectra of the signal
plot(w,abs(fftshift(X)))

end