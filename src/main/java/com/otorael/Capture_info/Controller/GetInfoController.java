package com.otorael.Capture_info.Controller;

import com.otorael.Capture_info.ResponseDTO.InformationResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class GetInfoController {

    @RequestMapping(value = "/public/get-info", method = RequestMethod.GET)
    public ResponseEntity<?> getIpAddress(HttpServletRequest request){

        String Ipaddress = request.getHeader("X-Forwarded-For");
        String Protocol = request.getProtocol();

        InformationResponseDTO outPut = new InformationResponseDTO(
                "Success",
                Ipaddress,
                Protocol,
                "Network Information returned successfully"
        );
        return ResponseEntity.status(HttpStatus.OK).body(outPut);
    }
}
