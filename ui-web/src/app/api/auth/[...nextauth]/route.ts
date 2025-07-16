import NextAuth, {NextAuthOptions} from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import LoginService from "@/app/lib/LoginService";

const host = process.env.API_BASE_URI || "http://0.0.0.0:8080";

export const authOptions: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        accountId: {label: "accountId", type: "text"},
        password: {label: "password", type: "password"},
      },
      async authorize(credentials) {
        const service = new LoginService(host);
        // @ts-ignore
        const response = await service.login(credentials.accountId, credentials.password)
        if (response.status == 200) {
          return response.data; // BFF returns user object on success
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
