let slides = document.querySelectorAll('.slide');
let currentIndex = 0;

function showSlide(index) {
    // Hide all slides (images and videos)
    slides.forEach(slide => {
        slide.classList.remove('active');
        if (slide.tagName === "VIDEO") {
            slide.pause(); // Pause videos when hidden
            slide.currentTime = 0; // Reset video to the beginning
        }
    });

    // Show the current slide
    let currentSlide = slides[index];
    currentSlide.classList.add('active');

    // If the slide is a video, play it
    if (currentSlide.tagName === "VIDEO") {
        currentSlide.play();
    }
}

function nextSlide() {
    currentIndex = (currentIndex + 1) % slides.length; // Move to next slide
    showSlide(currentIndex);
}

// Start the slideshow and change slides every 3 seconds
setInterval(nextSlide, 3000);
