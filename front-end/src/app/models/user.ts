import Role from "./role";

export default interface User {
  id: number | null;
  email: string | null;
  name: string | null;
  lastName: string | null;
  password: string | null;
  bio: string | null;
  areaOfInterest: string | null;
  profilePictureUrl: string | null,
  roles: Role[] | null;
}
