export const MSG = function (
    msg?: string | number,
    data?: any,
    other?: any
) {
    return {
        msg,
        data,
        other
    }
}