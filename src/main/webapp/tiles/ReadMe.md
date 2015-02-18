Use DeepZoom to create tiles

clone this repo https://github.com/openzoom/deepzoom.py

install deepzoom

# install libjpeg-dev with apt
  sudo apt-get install libjpeg-dev

# reinstall pillow
  sudo pip install -I pillow

# install PIL
  pip install PIL --allow-external PIL --allow-unverified PIL

#Install DeepZoom

   git clone https://github.com/openzoom/deepzoom.py.git
   cd deepzoom.py
   sudo python setup.py install

   ## Example

       cd deepzoom.py/examples/helloworld/
       ./helloworld.py

Next you have to create a specific json file for your dzi.
Look at helloworld.js I provided and update with the info from your image
