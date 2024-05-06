import { Provider } from '@nestjs/common';
import * as admin from 'firebase-admin';
import * as fs from 'fs';
export const MessagingProvider = 'lib:messaging';

export const messagingProvider: Provider = {
  provide: MessagingProvider,
  useFactory: async () => {
    const jsonString = fs.readFileSync(
    "./serviceAccountKey.json",
      'utf-8',
    );
    const jsonData = JSON.parse(jsonString);
    await admin.initializeApp({
      credential: admin.credential.cert({
        projectId: jsonData.project_id,
        clientEmail: jsonData.client_email,
        privateKey: jsonData.private_key,
      }),
    });
    return admin.messaging(admin.app());
  },
};