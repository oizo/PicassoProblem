# PicassoProblem

It seems Picasso has a bug while loading images on Android 4.1.2/4.1.1.
The load time for loading three images from web, is usually 10-20 seconds,
while trying to load the same three images with Android Universal Image Loader 
takes around a second.

I've come across the problems on a Samsung Galaxy SIII Mini (GT-I8190N) running 
Android 4.1.2. And i've gotten a single complaing about load times on a 
LG Optimus G (LG-E975), though the LG isn't verified as I haven't got access to 
such a device.

I've easily been able to reproduce the problem in this demo app on the GT-I8190N. 
The bug also seems to be reproducable on an Android Emulator running 
Android 4.1.1 (SDK 16), available via the AVD Manager.
