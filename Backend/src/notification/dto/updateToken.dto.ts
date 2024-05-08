import { ApiProperty } from "@nestjs/swagger"

export class UpdateTokenDTO{
    @ApiProperty()
    idDevice:string 
    @ApiProperty()
    idUser:string
    @ApiProperty()
    token:string
}