function Banner() {
  return (
    <div className="container">
      <img
        className="banner-image"
        srcSet="/banner3-360w.jpg 360w, 
        /banner2-1084w.jpg 1084w, 
        /banner-1400w.webp 1400w"
        src="/banner3-360w.jpg"
        alt="banner image"
      ></img>
    </div>
  );
}

export default Banner;
