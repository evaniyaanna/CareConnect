import {
    createBrowserRouter
} from "react-router-dom";


import App
    from "./App";


import LandingPage
    from "./pages/LandingPage";


import Home
    from "./pages/Home";


import DoctorList
    from "./pages/DoctorList";


import Register
    from "./pages/Register";


import Login
    from "./pages/Login";


import DoctorDetails
    from "./pages/DoctorDetails";


import Appointments
    from "./pages/Appointments";


import CheckAuth
    from "./auth/checkAuth";


import BookAppointment
    from "./pages/BookAppointment";


import ChangePassword
    from "./pages/ChangePassword";


const router =

    createBrowserRouter([

        {

            path: "",

            element: <App />,

            children: [

                /*
                 * First page when application opens.
                 */

                {
                    path: "",
                    element: <LandingPage />
                },


                /*
                 * Existing Home page.
                 */

                {
                    path: "home",
                    element: <Home />
                },


                /*
                 * Doctors
                 */

                {
                    path: "doctors",
                    element: <DoctorList />
                },


                {
                    path: "doctors/:id",
                    element: <DoctorDetails />
                },


                /*
                 * Authentication pages
                 */

                {
                    path: "register",
                    element: <Register />
                },


                {
                    path: "login",
                    element: <Login />
                },


                /*
                 * Appointments
                 */

                {
                    path: "appointments",

                    element: (

                        <CheckAuth>

                            <Appointments />

                        </CheckAuth>

                    )

                },


                /*
                 * Book Appointment
                 */

                {
                    path: "appointments/book",

                    element: (

                        <CheckAuth>

                            <BookAppointment />

                        </CheckAuth>

                    )

                },


                /*
                 * Change Password
                 */

                {
                    path: "change-password",

                    element: (

                        <CheckAuth>

                            <ChangePassword />

                        </CheckAuth>

                    )

                }

            ]

        }

    ]);


export default router;