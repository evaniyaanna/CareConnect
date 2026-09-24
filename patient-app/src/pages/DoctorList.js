import {
    useEffect,
    useState
} from "react";

import DoctorCard
    from "../components/DoctorCard";

import api from "../api/axios";


function DoctorList() {

    const [doctors, setDoctors] =
        useState([]);

    const [search, setSearch] =
        useState("");

    const [searchText, setSearchText] =
        useState("");


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


    const handleSearch = (event) => {

        event.preventDefault();

        setSearchText(
            search.trim().toLowerCase()
        );
    };


    const filteredDoctors =
        doctors.filter(doctor => {

            const name =
                doctor.name
                    ?.toLowerCase() || "";

            const specialization =
                doctor.specialization
                    ?.toLowerCase() || "";

            return (
                name.includes(searchText) ||
                specialization.includes(searchText)
            );
        });


    return (

        <div>

            <h2 className="mb-4">
                Our Doctors
            </h2>


            {/* Doctor Search */}

            <form
                onSubmit={handleSearch}
                className="row g-2 mb-4"
            >

                <div className="col-md-6">

                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search by doctor name or specialization"
                        value={search}
                        onChange={
                            event =>
                                setSearch(
                                    event.target.value
                                )
                        }
                    />

                </div>


                <div className="col-md-2">

                    <button
                        type="submit"
                        className="btn btn-primary w-100"
                    >
                        Search
                    </button>

                </div>

            </form>


            {/* No Doctors */}

            {filteredDoctors.length === 0 && (

                <div className="alert alert-info">

                    No doctors found.

                </div>

            )}


            {/* Doctor Cards */}

            <div className="row">

                {filteredDoctors.map(
                    doctor => (

                        <div
                            className="col-md-4 mb-4"
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
    );
}


export default DoctorList;