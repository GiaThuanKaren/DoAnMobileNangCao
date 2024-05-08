import { ApiProperty } from "@nestjs/swagger"

export class message_notifyDTO {
    @ApiProperty()
    message: string
    @ApiProperty()

    userid: string
}