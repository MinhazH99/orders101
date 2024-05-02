function Banner() {
  return (
    <div className="container">
      <img
        className="banner-image"
        srcSet="../src/assets/banner3-360w.jpg 360w, 
        ../src/assets/banner2-1084w.jpg 1084w, 
        ../src/assets/banner-1400w.webp 1400w"
        src="../src/assets//banner3-360w.jpg"
        alt="banner image"
      ></img>
    </div>
  );
}

export default Banner;
