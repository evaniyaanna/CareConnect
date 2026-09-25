import {
    useEffect,
    useState
} from "react";


import {
    useNavigate
} from "react-router-dom";


import "./LandingPage.css";


/*
 * Hospital images from public/images
 */

const images = [

    "/images/hospital1.jpg",

    "/images/hospital2.jpg",

    "/images/hospital3.jpg",

    "/images/hospital4.jpg"

];


const quotes = [

    "Your health is our priority.",

    "Better healthcare begins with better connections.",

    "Connecting you with the care you deserve."

];


function LandingPage() {

    const navigate =
        useNavigate();


    const [
        currentImage,
        setCurrentImage
    ] = useState(0);


    /*
     * Automatically change carousel image
     * every 4 seconds.
     */

    useEffect(() => {

        const interval =

            setInterval(() => {

                setCurrentImage(

                    previous =>

                        (previous + 1)
                        % images.length

                );

            }, 4000);


        return () =>

            clearInterval(interval);

    }, []);


    /*
     * Move to previous image.
     */

    const previousImage = () => {

        setCurrentImage(

            previous =>

                (previous - 1 + images.length)
                % images.length

        );

    };


    /*
     * Move to next image.
     */

    const nextImage = () => {

        setCurrentImage(

            previous =>

                (previous + 1)
                % images.length

        );

    };


    /*
     * Go to existing Home page.
     */

    const startNow = () => {

        navigate("/home");

    };


    return (

        <div className="landing-page">


            {/* ==================================
                HERO SECTION
            ================================== */}

            <section
                className="landing-hero"
                style={{
                    backgroundImage:
                        `url(${images[0]})`
                }}
            >

                <div className="hero-overlay"></div>


                <div className="hero-content">

                    <p className="hero-small-title">
                        WELCOME TO
                    </p>


                    <h1>
                        CareConnect
                    </h1>


                    <p className="hero-tagline">

                        Connecting You to
                        Better Healthcare

                    </p>


                    <p className="hero-description">

                        Find trusted doctors,
                        manage your appointments,
                        and take control of
                        your healthcare journey.

                    </p>


                    <button
                        className="hero-button"
                        onClick={startNow}
                    >

                        START NOW

                    </button>

                </div>


                <div className="scroll-indicator">

                    <span>
                        Scroll to explore
                    </span>

                    <div>
                        ↓
                    </div>

                </div>

            </section>


            {/* ==================================
                ABOUT SECTION
            ================================== */}

            <section className="about-section">

                <div className="section-content">

                    <p className="section-label">
                        ABOUT CARECONNECT
                    </p>


                    <h2>
                        Healthcare Made Simple
                    </h2>


                    <p className="about-text">

                        CareConnect brings patients
                        and healthcare services together
                        in one convenient place.

                        Find doctors, book appointments,
                        and manage your healthcare journey
                        with ease.

                    </p>


                    <div className="feature-row">


                        <div className="feature-card">

                            <div className="feature-icon">
                                +
                            </div>

                            <h3>
                                Trusted Care
                            </h3>

                            <p>
                                Connect with healthcare
                                professionals.
                            </p>

                        </div>


                        <div className="feature-card">

                            <div className="feature-icon">
                                ✓
                            </div>

                            <h3>
                                Easy Booking
                            </h3>

                            <p>
                                Book appointments quickly
                                and conveniently.
                            </p>

                        </div>


                        <div className="feature-card">

                            <div className="feature-icon">
                                ♥
                            </div>

                            <h3>
                                Patient First
                            </h3>

                            <p>
                                Healthcare designed around you.
                            </p>

                        </div>


                    </div>

                </div>

            </section>


            {/* ==================================
                CAROUSEL SECTION
            ================================== */}

            <section className="carousel-section">

                <p className="section-label">
                    OUR HEALTHCARE ENVIRONMENT
                </p>


                <h2>
                    A Place Where Care Comes First
                </h2>


                <div className="carousel">

                    <img
                        src={images[currentImage]}
                        alt="Hospital"
                        className="carousel-image"
                    />


                    <div className="carousel-overlay">

                        <span>
                            CareConnect
                        </span>

                    </div>


                    <button
                        className="carousel-button left"
                        onClick={previousImage}
                        aria-label="Previous image"
                    >
                        ❮
                    </button>


                    <button
                        className="carousel-button right"
                        onClick={nextImage}
                        aria-label="Next image"
                    >
                        ❯
                    </button>

                </div>


                <div className="carousel-dots">

                    {images.map(

                        (_, index) => (

                            <button
                                key={index}
                                className={
                                    index === currentImage
                                        ? "dot active"
                                        : "dot"
                                }
                                onClick={() =>
                                    setCurrentImage(index)
                                }
                                aria-label={
                                    `View hospital ${index + 1}`
                                }
                            />

                        )

                    )}

                </div>

            </section>


            {/* ==================================
                QUOTES SECTION
            ================================== */}

            <section className="quotes-section">

                <p className="section-label">
                    OUR PROMISE
                </p>


                <h2>
                    Healthcare That Cares
                </h2>


                <div className="quote-container">

                    {quotes.map(

                        (quote, index) => (

                            <div
                                className="quote-card"
                                key={index}
                            >

                                <div className="quote-icon">
                                    "
                                </div>


                                <p>
                                    {quote}
                                </p>


                                <span>
                                    — CareConnect
                                </span>

                            </div>

                        )

                    )}

                </div>

            </section>


            {/* ==================================
                FINAL CTA SECTION
            ================================== */}

            <section
                className="start-section"
                style={{
                    backgroundImage: `
                        linear-gradient(
                            rgba(0, 70, 120, 0.82),
                            rgba(0, 70, 120, 0.82)
                        ),
                        url(${images[1]})
                    `
                }}
            >

                <div className="start-content">

                    <p className="section-label light">
                        YOUR HEALTH. YOUR JOURNEY.
                    </p>


                    <h2>
                        Ready to take care
                        of your health?
                    </h2>


                    <p>

                        Find your doctor and manage
                        your appointments with CareConnect.

                    </p>


                    <button
                        className="start-button"
                        onClick={startNow}
                    >

                        START NOW

                    </button>

                </div>

            </section>


            {/* ==================================
                LANDING FOOTER
            ================================== */}

            <footer className="landing-footer">

                <h3>
                    CareConnect
                </h3>


                <p>
                    Connecting patients
                    with better healthcare.
                </p>


                <p className="copyright">
                    © 2026 CareConnect.
                    All rights reserved.
                </p>

            </footer>


        </div>

    );

}


export default LandingPage;