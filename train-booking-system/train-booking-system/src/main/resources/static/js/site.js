document.addEventListener('DOMContentLoaded', function () {
  var carouselEl = document.querySelector('#homeCarousel');
  if (!carouselEl) return;
  carouselEl.addEventListener('mouseenter', function () {
    var carousel = bootstrap.Carousel.getInstance(carouselEl);
    if (carousel) carousel.pause();
  });
  carouselEl.addEventListener('mouseleave', function () {
    var carousel = bootstrap.Carousel.getInstance(carouselEl);
    if (carousel) carousel.cycle();
  });
});
