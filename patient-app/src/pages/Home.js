import {
    useEffect,
    useState
} from "react";

import DoctorCard
    from "../components/DoctorCard";

import api
    from "../api/axios";

import "./Home.css";


function Home() {

    const [doctors, setDoctors] =
        useState([]);


    useEffect(() => {

        loadDoctors();

    }, []);


    const loadDoctors = async () => {

        try {

            const response =
                await api.get("/doctors");

            setDoctors(response.data);

        } catch (error) {

            console.error(
                "Unable to load doctors.",
                error
            );

        }

    };


    return (

        <div className="home-page">


            {/* =========================================
                HOSPITAL BOOKING
            ========================================= */}

            <section className="booking-section">

                <div className="booking-content">

                    <span className="booking-small-title">
                        WELCOME TO CARECONNECT
                    </span>


                    <h1>
                        Hospital Booking
                    </h1>


                    <p className="booking-lead">

                        Find doctors and manage your
                        appointments easily.

                    </p>


                    <div className="booking-line"></div>


                    <p className="booking-text">

                        Browse our doctors and book
                        an appointment.

                    </p>

                </div>

            </section>



            {/* =========================================
                OUR DOCTORS
            ========================================= */}

            <section className="home-doctors-section">

                <div className="doctors-heading">

                    <span className="home-section-label">
                        OUR HEALTHCARE TEAM
                    </span>


                    <h2>
                        Our Doctors
                    </h2>


                    <p>

                        Meet our experienced healthcare
                        professionals and find the right
                        doctor for your needs.

                    </p>

                </div>



                {doctors.length === 0 && (

                    <div className="home-doctors-empty">

                        <p>
                            No doctors available.
                        </p>

                    </div>

                )}



                {doctors.length > 0 && (

                    <div className="doctor-scroll-wrapper">

                        <div className="doctor-scroll">

                            {doctors.map(
                                doctor => (

                                    <div
                                        className="home-doctor-item"
                                        key={doctor.id}
                                    >

                                        <DoctorCard
                                            doctor={doctor}
                                        />

                                    </div>

                                )
                            )}

                        </div>

                    </div>

                )}

            </section>



            {/* =========================================
                HOSPITAL IMAGE SECTION
            ========================================= */}

            <section
                className="healthcare-message"

                style={{
                    backgroundImage: `
                        linear-gradient(
                            rgba(0, 50, 90, 0.78),
                            rgba(0, 80, 135, 0.78)
                        ),
                        url("/images/hospital2.jpg")
                    `
                }}
            >

                <div className="healthcare-message-content">

                    <span>
                        CARECONNECT
                    </span>


                    <h2>
                        Your Health,
                        <br />
                        Our Priority
                    </h2>


                    <p>

                        Connect with healthcare
                        professionals and take control
                        of your healthcare journey.

                    </p>

                </div>

            </section>



            {/* =========================================
                INFORMATION CARDS
            ========================================= */}

            <section className="home-info-section">

                <div className="home-info-container">


                    <div className="home-info-card">

                        <div className="home-info-icon">
                            +
                        </div>


                        <h3>
                            Trusted Care
                        </h3>


                        <p>

                            Connect with experienced
                            healthcare professionals.

                        </p>

                    </div>



                    <div className="home-info-card">

                        <div className="home-info-icon">
                            ✓
                        </div>


                        <h3>
                            Easy Appointments
                        </h3>


                        <p>

                            Book and manage your
                            appointments easily.

                        </p>

                    </div>



                    <div className="home-info-card">

                        <div className="home-info-icon">
                            ♥
                        </div>


                        <h3>
                            Patient First
                        </h3>


                        <p>

                            Healthcare designed
                            around your needs.

                        </p>

                    </div>


                </div>

            </section>


        </div>

    );

}


export default Home;