import {
    useEffect,
    useState
} from "react";

import {
    useParams,
    useNavigate
} from "react-router-dom";

import api
    from "../api/axios";

import "./DoctorDetails.css";


function DoctorDetails() {

    const { id } =
        useParams();


    const navigate =
        useNavigate();


    const [doctor, setDoctor] =
        useState(null);


    useEffect(() => {

        loadDoctor();

    }, [id]);


    const loadDoctor = async () => {

        const response =
            await api.get(
                `/doctors/${id}`
            );

        setDoctor(
            response.data
        );

    };


    if (!doctor) {

        return (

            <div className="doctor-loading">

                <div className="doctor-loading-card">

                    <div className="loading-spinner"></div>

                    <p>
                        Loading doctor details...
                    </p>

                </div>

            </div>

        );

    }


    return (

        <div className="doctor-details-page">


            {/* =========================================
                PAGE HEADING
            ========================================= */}

            <div className="doctor-details-heading">

                <span>
                    CARECONNECT
                </span>

                <h1>
                    Doctor Details
                </h1>

                <p>
                    Get to know your healthcare professional
                    before booking your appointment.
                </p>

            </div>



            {/* =========================================
                DOCTOR CARD
            ========================================= */}

            <div className="doctor-profile-card">


                {/* Doctor image */}

                <div className="doctor-profile-image">

                    <img
                        src="/images/doctor.jpg"
                        alt="Doctor"
                    />

                </div>



                {/* Doctor name */}

                <h2>
                    {doctor.name}
                </h2>


                <div className="doctor-specialization">

                    {doctor.specialization}

                </div>



                {/* Divider */}

                <div className="doctor-divider"></div>



                {/* Doctor information */}

                <div className="doctor-information">


                    <div className="doctor-info-row">

                        <div className="doctor-info-label">

                            <span className="info-icon">
                                🎓
                            </span>

                            Qualification

                        </div>


                        <div className="doctor-info-value">

                            {doctor.qualification}

                        </div>

                    </div>



                    <div className="doctor-info-row">

                        <div className="doctor-info-label">

                            <span className="info-icon">
                                💼
                            </span>

                            Experience

                        </div>


                        <div className="doctor-info-value">

                            {doctor.experience}
                            {" "}
                            years

                        </div>

                    </div>



                    <div className="doctor-info-row">

                        <div className="doctor-info-label">

                            <span className="info-icon">
                                ₹
                            </span>

                            Consultation Fee

                        </div>


                        <div className="doctor-info-value">

                            ₹
                            {doctor.consultationFee}

                        </div>

                    </div>


                </div>



                {/* =====================================
                    BOOK APPOINTMENT
                ===================================== */}

                <button
                    type="button"
                    className="doctor-book-button"
                    onClick={() =>
                        navigate(
                            "/appointments/book"
                        )
                    }
                >

                    Book Appointment

                    <span>
                        →
                    </span>

                </button>


                {/* Back button */}

                <button
                    type="button"
                    className="doctor-back-button"
                    onClick={() =>
                        navigate("/home")
                    }
                >

                    ← Back to Home

                </button>


            </div>


        </div>

    );

}


export default DoctorDetails;