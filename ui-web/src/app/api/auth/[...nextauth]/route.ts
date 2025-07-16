import NextAuth, {NextAuthOptions} from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

const host = process.env.API_BASE_URI || "http://0.0.0.0:8080";

export const authOptions: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        accountId: {label: "accountId", type: "text"},
        password: {label: "Password", type: "password"},
      },
      async authorize(credentials) {
        const res = await fetch(`${host}/login`, {
          method: "POST",
          body: JSON.stringify(credentials),
          headers: {"Content-Type": "application/json"},
        });
        const user = await res.json();
        if (res.ok && user) {
          return user; // BFF returns user object on success
        }
        return null;
      },
    }),
  ],
  session: {strategy: "jwt"},
  pages: {signIn: "/login"}, // Redirect to login page
};

const handler = NextAuth(authOptions);
export {handler as GET, handler as POST};
